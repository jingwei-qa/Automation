#pragma once


//-------------------------------------------------------------------------------------------------
#ifndef interface
#define interface struct
#endif
#ifndef _rect_
#define _rect_
struct rect{ short x0, y0, x1, y1; };
#endif
#ifndef byte
typedef unsigned char byte;
#endif
#ifndef word
typedef unsigned short word;
#endif


//-------------------------------------------------------------------------------------------------
enum ocr_attribute
{
	//Android和iOS组使用
	NAMECARD_NAME_EN,    //英文姓名
	NAMECARD_NAME_CN,	 //中文姓名
	NAMECARD_TITLE,      //职称
	NAMECARD_TELPHONE,   //电话
	NAMECARD_FAX,        //传真
	NAMECARD_CELLPHONE,  //手机
	NAMECARD_EMAIL,      //邮箱
	NAMECARD_WEBURL,     //主页
	NAMECARD_IM,         //如QQ
	NAMECARD_ADDRESS,    //地址
	NAMECARD_POSTCODE,   //邮编
	NAMECARD_COMPANY,    //公司
	NAMECARD_DEPARTMENT, //部门
	NAMECARD_UNKNOWN,    //未知
	//OCR组使用信息
	NAMECARD_BBCALL,     //传呼, 以电话输出
	NAMECARD_OTHER       //如统一编号，以未知输出
};


//-------------------------------------------------------------------------------------------------
struct ocr_result
{
	//Android和iOS组使用
	int   nblock;			//文本块的数目
	int   attri[128];		//文本块的属性
	int   nchar[128];		//块内字符的个数
	word  wchar[128][256];	//块内字符的值
	int   wblock[128];      //块的宽度
	int   hblock[128];      //块的高度
	byte *pblock[128];      //块的二值数据[wblock*hblock], 需调用ReleaseImage(pblock[i])
	//OCR组使用信息
	rect  rblock[128];		//文本块的位置
	rect  rchar[128][256];	//块内字符的位置
	float fchar[128][256];	//块内字符的置信度
};


//-------------------------------------------------------------------------------------------------
//OCR组使用信息
#ifndef _ocr_layout_
#define _ocr_layout_
struct  ocr_layout
{
	int   orient;      // 0: top, 1: left, 2: bottom, 3: right
	int   width;       // resized image width
	int   height;      // resized image height
	byte  *data;       // output image
	int   channel;  
	float fskew;       // global rotation angle
	int   nblock;      // number of blocks
	float fangle[128]; // rotation angles of blocks
	rect  rblock[128]; // rects of blocks on the rectified image
};
#endif


//-------------------------------------------------------------------------------------------------
interface INameCard
{
	//初始化一个接口实例对象, 建议在名片通开启拍摄名片前调用
	//szPathName - [in] 资源ocr_data文件夹的路径名
	static INameCard* CreateInstance(char *szPathName);

	//功能1: 透视矫正名片
	//nChannel	- [in] 图像数据的通道数, 如BGRA格式为4, BGR格式为3
	//fAngle   	- [in] 相机的视野角, 如Android的API getHorizontalViewAngle()可返回该值
	//界面可见区的定义, 以黑边为准
	//xFrame    - [in ] 界面可见区左上角x系数(0~1)
	//yFrame    - [in ] 界面可见区左上角y系数(0~1)
	//wFrame    - [in ] 界面可见区宽度系数(0~1)
	//hFrame    - [in ] 界面可见区高度系数(0~1)
	//拍照得到的彩色图像
	//nWidth0	- [in] 图像的宽度, 要求nWidth0>nHeight0
	//nHeight0	- [in] 图像的高度
	//pColor0	- [in] 图像的数据, 长度为nChannel*nWidth0*nHeight0
	//输出显示名片四边的结果图像, 用于用户确认或重拍
	//nWidth1	- [out] 图像的宽度为640
	//nHeight1	- [out] 图像的高度
	//pColor1	- [out] 图像的数据, 长度为nChannel*nWidth1*nHeight1
	//输出透视校正后的彩色图像, 当透视矫正成功, 按名片边缘裁剪的
	//                      当透视矫正失败, 按可见区域裁剪的
	//nWidth2	- [out] 图像的宽度为1200
	//nHeight2	- [out] 图像的高度
	//pColor2	- [out] 图像的数据, 长度为nChannel*nWidth2*nHeight2
	//返回透视矫正是否成功的标识
	virtual bool RectifyNameCard(int nChannel, float fAngle,
		                         float xFrame, float yFrame, float wFrame, float hFrame, //可见区域
		                         int nWidth0, int nHeight0, byte *pColor0, //原彩图
								 int&nWidth1, int&nHeight1, byte*&pColor1, //小彩图(画了名片四边)
		                         int&nWidth2, int&nHeight2, byte*&pColor2  //新彩图(校正后的)
								 ) = 0;
	
	//RectifyNameCard()输出的图像必须释放，否则造成内存泄漏
	//pColor	- [in] 分别释放pColor1和pColor2
	virtual void ReleaseImage(byte* &pColor) = 0; 

	//功能2: 识别名片文字
	//bPersp	- [in] 输入图像是否已做透视矫正
	//nChannel	- [in] 图像数据的通道数，如BGRA格式为4，BGR格式为3
	//输入拍照得到的彩色图像pColor0或者透视矫正后的彩色图像pColor2
	//nWidth	- [in] 图像的宽度，要求nWidth>nHeight
	//nHeight	- [in] 图像的高度
	//pColor	- [in] 图像的数据，长度为nChannel*nWidth*nHeight
	//输出识别结果
	//pResult   -[out] 名片的识别结果, 仅给pResult指针赋值，无需外面释放pResult
	//返回名片识别是否成功的标识
	virtual bool RecognizeNameCard(bool bPersp, int nChannel, 
		                           int nWidth, int nHeight, byte *pColor,
								   ocr_result* &pResult
								   ) = 0;
	
	//返回版面分析的结果
	virtual ocr_layout* GetLayout() = 0; //OCR组使用信息
	
	//释放一个接口实例对象和其资源，建议在离开拍摄名片时调用一次
	virtual void Release() = 0;
};

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
	//Android��iOS��ʹ��
	NAMECARD_NAME_EN,    //Ӣ������
	NAMECARD_NAME_CN,	 //��������
	NAMECARD_TITLE,      //ְ��
	NAMECARD_TELPHONE,   //�绰
	NAMECARD_FAX,        //����
	NAMECARD_CELLPHONE,  //�ֻ�
	NAMECARD_EMAIL,      //����
	NAMECARD_WEBURL,     //��ҳ
	NAMECARD_IM,         //��QQ
	NAMECARD_ADDRESS,    //��ַ
	NAMECARD_POSTCODE,   //�ʱ�
	NAMECARD_COMPANY,    //��˾
	NAMECARD_DEPARTMENT, //����
	NAMECARD_UNKNOWN,    //δ֪
	//OCR��ʹ����Ϣ
	NAMECARD_BBCALL,     //����, �Ե绰���
	NAMECARD_OTHER       //��ͳһ��ţ���δ֪���
};


//-------------------------------------------------------------------------------------------------
struct ocr_result
{
	//Android��iOS��ʹ��
	int   nblock;			//�ı������Ŀ
	int   attri[128];		//�ı��������
	int   nchar[128];		//�����ַ��ĸ���
	word  wchar[128][256];	//�����ַ���ֵ
	int   wblock[128];      //��Ŀ��
	int   hblock[128];      //��ĸ߶�
	byte *pblock[128];      //��Ķ�ֵ����[wblock*hblock], �����ReleaseImage(pblock[i])
	//OCR��ʹ����Ϣ
	rect  rblock[128];		//�ı����λ��
	rect  rchar[128][256];	//�����ַ���λ��
	float fchar[128][256];	//�����ַ������Ŷ�
};


//-------------------------------------------------------------------------------------------------
//OCR��ʹ����Ϣ
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
	//��ʼ��һ���ӿ�ʵ������, ��������Ƭͨ����������Ƭǰ����
	//szPathName - [in] ��Դocr_data�ļ��е�·����
	static INameCard* CreateInstance(char *szPathName);

	//����1: ͸�ӽ�����Ƭ
	//nChannel	- [in] ͼ�����ݵ�ͨ����, ��BGRA��ʽΪ4, BGR��ʽΪ3
	//fAngle   	- [in] �������Ұ��, ��Android��API getHorizontalViewAngle()�ɷ��ظ�ֵ
	//����ɼ����Ķ���, �Ժڱ�Ϊ׼
	//xFrame    - [in ] ����ɼ������Ͻ�xϵ��(0~1)
	//yFrame    - [in ] ����ɼ������Ͻ�yϵ��(0~1)
	//wFrame    - [in ] ����ɼ������ϵ��(0~1)
	//hFrame    - [in ] ����ɼ����߶�ϵ��(0~1)
	//���յõ��Ĳ�ɫͼ��
	//nWidth0	- [in] ͼ��Ŀ��, Ҫ��nWidth0>nHeight0
	//nHeight0	- [in] ͼ��ĸ߶�
	//pColor0	- [in] ͼ�������, ����ΪnChannel*nWidth0*nHeight0
	//�����ʾ��Ƭ�ıߵĽ��ͼ��, �����û�ȷ�ϻ�����
	//nWidth1	- [out] ͼ��Ŀ��Ϊ640
	//nHeight1	- [out] ͼ��ĸ߶�
	//pColor1	- [out] ͼ�������, ����ΪnChannel*nWidth1*nHeight1
	//���͸��У����Ĳ�ɫͼ��, ��͸�ӽ����ɹ�, ����Ƭ��Ե�ü���
	//                      ��͸�ӽ���ʧ��, ���ɼ�����ü���
	//nWidth2	- [out] ͼ��Ŀ��Ϊ1200
	//nHeight2	- [out] ͼ��ĸ߶�
	//pColor2	- [out] ͼ�������, ����ΪnChannel*nWidth2*nHeight2
	//����͸�ӽ����Ƿ�ɹ��ı�ʶ
	virtual bool RectifyNameCard(int nChannel, float fAngle,
		                         float xFrame, float yFrame, float wFrame, float hFrame, //�ɼ�����
		                         int nWidth0, int nHeight0, byte *pColor0, //ԭ��ͼ
								 int&nWidth1, int&nHeight1, byte*&pColor1, //С��ͼ(������Ƭ�ı�)
		                         int&nWidth2, int&nHeight2, byte*&pColor2  //�²�ͼ(У�����)
								 ) = 0;
	
	//RectifyNameCard()�����ͼ������ͷţ���������ڴ�й©
	//pColor	- [in] �ֱ��ͷ�pColor1��pColor2
	virtual void ReleaseImage(byte* &pColor) = 0; 

	//����2: ʶ����Ƭ����
	//bPersp	- [in] ����ͼ���Ƿ�����͸�ӽ���
	//nChannel	- [in] ͼ�����ݵ�ͨ��������BGRA��ʽΪ4��BGR��ʽΪ3
	//�������յõ��Ĳ�ɫͼ��pColor0����͸�ӽ�����Ĳ�ɫͼ��pColor2
	//nWidth	- [in] ͼ��Ŀ�ȣ�Ҫ��nWidth>nHeight
	//nHeight	- [in] ͼ��ĸ߶�
	//pColor	- [in] ͼ������ݣ�����ΪnChannel*nWidth*nHeight
	//���ʶ����
	//pResult   -[out] ��Ƭ��ʶ����, ����pResultָ�븳ֵ�����������ͷ�pResult
	//������Ƭʶ���Ƿ�ɹ��ı�ʶ
	virtual bool RecognizeNameCard(bool bPersp, int nChannel, 
		                           int nWidth, int nHeight, byte *pColor,
								   ocr_result* &pResult
								   ) = 0;
	
	//���ذ�������Ľ��
	virtual ocr_layout* GetLayout() = 0; //OCR��ʹ����Ϣ
	
	//�ͷ�һ���ӿ�ʵ�����������Դ���������뿪������Ƭʱ����һ��
	virtual void Release() = 0;
};

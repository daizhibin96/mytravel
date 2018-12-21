package com.cn.travel.utils;



import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sendtion on 2016/6/24.
 */
public class StringUtils {


    /**
     * @param targetStr Ҫ������ַ���
     * @description �и��ַ��������ı���img��ǩ��Ƭ������"ab<img>cd"ת��Ϊ"ab"��"<img>"��"cd"
     */
    public static List<String> cutStringByImgTag(String targetStr) {
        List<String> splitTextList = new ArrayList<String>();
        Pattern pattern = Pattern.compile("<img.*?src=\\\"(.*?)\\\".*?>");
        Matcher matcher = pattern.matcher(targetStr);
        int lastIndex = 0;
        while (matcher.find()) {
            if (matcher.start() > lastIndex) {
                splitTextList.add(targetStr.substring(lastIndex, matcher.start()));
            }
            splitTextList.add(targetStr.substring(matcher.start(), matcher.end()));
            lastIndex = matcher.end();
        }
        if (lastIndex != targetStr.length()) {
            splitTextList.add(targetStr.substring(lastIndex, targetStr.length()));
        }
        return splitTextList;
    }

    /**
     * ��ȡimg��ǩ�е�srcֵ
     * @param content
     * @return
     */
    public static String getImgSrc(String content){
        String str_src = null;
        //Ŀǰimg��ǩ��ʾ��3�ֱ��ʽ
        //<img alt="" src="1.jpg"/>   <img alt="" src="1.jpg"></img>     <img alt="" src="1.jpg">
        //��ʼƥ��content�е�<img />��ǩ
        Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
        Matcher m_img = p_img.matcher(content);
        boolean result_img = m_img.find();
        if (result_img) {
            while (result_img) {
                //��ȡ��ƥ���<img />��ǩ�е�����
                String str_img = m_img.group(2);

                //��ʼƥ��<img />��ǩ�е�src
                Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
                Matcher m_src = p_src.matcher(str_img);
                if (m_src.find()) {
                    str_src = m_src.group(3);
                }
                //����ƥ��<img />��ǩ�е�src

                //ƥ��content���Ƿ������һ��<img />��ǩ������������ϲ���ƥ��<img />��ǩ�е�src
                result_img = m_img.find();
            }
        }
        return str_src;
    }

    /**
     * �ؼ��ָ�����ʾ
     * @param target  ��Ҫ�����Ĺؼ���
     * @param text	     ��Ҫ��ʾ������
     * @return spannable �������Ľ�����ǵò�ҪtoString()������û��Ч��
     * SpannableStringBuilder textString = TextUtilTools.highlight(item.getItemName(), KnowledgeActivity.searchKey);
     * vHolder.tv_itemName_search.setText(textString);
     */
    public static SpannableStringBuilder highlight(String text, String target) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        CharacterStyle span = null;

        Pattern p = Pattern.compile(target);
        Matcher m = p.matcher(text);
        while (m.find()) {
            span = new ForegroundColorSpan(Color.parseColor("#EE5C42"));// ��Ҫ�ظ���
            spannable.setSpan(span, m.start(), m.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

    /**
     * ��html�ı�����ȡͼƬ��ַ�������ı�����
     * @param html ����html�ı�
     * @param isGetImage true��ȡͼƬ��false��ȡ�ı�
     * @return
     */
    public static ArrayList<String> getTextFromHtml(String html, boolean isGetImage){
        ArrayList<String> imageList = new ArrayList<>();
        ArrayList<String> textList = new ArrayList<>();
        //����img��ǩ�ָ��ͼƬ���ַ���
        List<String> list = cutStringByImgTag(html);
        for (int i = 0; i < list.size(); i++) {
            String text = list.get(i);
            if (text.contains("<img") && text.contains("src=")) {
                //��img��ǩ�л�ȡͼƬ��ַ
                String imagePath = getImgSrc(text);
                imageList.add(imagePath);
            } else {
                textList.add(text);
            }
        }
        //�ж��ǻ�ȡͼƬ�����ı�
        if (isGetImage) {
            return imageList;
        } else {
            return textList;
        }
    }

}


package com.linguancheng.gdeiassistant.Service.KaoYan;

import com.linguancheng.gdeiassistant.Enum.Base.ServiceResultEnum;
import com.linguancheng.gdeiassistant.Enum.Recognition.CheckCodeTypeEnum;
import com.linguancheng.gdeiassistant.Exception.CommonException.ServerErrorException;
import com.linguancheng.gdeiassistant.Exception.QueryException.ErrorQueryConditionException;
import com.linguancheng.gdeiassistant.Pojo.Entity.KaoYan;
import com.linguancheng.gdeiassistant.Pojo.Result.BaseResult;
import com.linguancheng.gdeiassistant.Service.Recognition.RecognitionService;
import com.linguancheng.gdeiassistant.Tools.ImageEncodeUtils;
import okhttp3.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Service
public class KaoYanService {

    @Autowired
    private RecognitionService recognitionService;

    private Log log = LogFactory.getLog(KaoYanService.class);

    /**
     * 查询考研初试成绩
     *
     * @param name
     * @param examNumber
     * @param idNumber
     * @return
     */
    public BaseResult<KaoYan, ServiceResultEnum> KaoYanScoreQuery(String name, String examNumber, String idNumber) {
        BaseResult<KaoYan, ServiceResultEnum> result = new BaseResult<>();
        try {
            OkHttpClient httpClient = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS).writeTimeout(5, TimeUnit.SECONDS).build();
            Request request = new Request.Builder().url("https://yz.chsi.com.cn/apply/cjcx/").build();
            Response response = httpClient.newCall(request).execute();
            Document document = Jsoup.parse(response.body().string());
            Element cjcxForm = document.getElementsByAttributeValue("name", "cjcxForm").first();
            boolean hasCheckCode = false;
            String checkcode = null;
            if (cjcxForm.getElementById("checkcode") != null) {
                //需要校验验证码
                hasCheckCode = true;
                String imageURL = "https://yz.chsi.com.cn" + cjcxForm.select("td[align='left']").get(5)
                        .select("img").first().attr("src");
                response = httpClient.newCall(new Request.Builder().url(imageURL).build()).execute();
                if (response.isSuccessful()) {
                    InputStream inputStream = response.body().byteStream();
                    String base64 = ImageEncodeUtils.ConvertToBase64(inputStream);
                    checkcode = recognitionService.CheckCodeRecognize(base64
                            , CheckCodeTypeEnum.NUMBER, 4);
                } else {
                    throw new ServerErrorException("查询系统异常");
                }
            }
            if (response.isSuccessful()) {
                FormBody.Builder builder = new FormBody.Builder().add("xm", name).add("zjhm", idNumber)
                        .add("ksbh", examNumber).add("ssdm", "")
                        .add("bkdwdm", "");
                if (hasCheckCode) {
                    builder.add("checkcode", checkcode);
                }
                RequestBody requestBody = builder.build();
                request = new Request.Builder().post(requestBody).url("https://yz.chsi.com.cn/apply/cjcx/cjcxAction.do")
                        .addHeader("Referer", "https://yz.chsi.com.cn/apply/cjcx/")
                        .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36").build();
                response = httpClient.newCall(request).execute();
                document = Jsoup.parse(response.body().string());
                if (response.isSuccessful()) {
                    Element ch_alert_message = document.getElementsByClass("ch-alert-message").first();
                    if (ch_alert_message != null) {
                        //查询条件错误
                        throw new ErrorQueryConditionException("查询条件错误");
                    }
                    Element container_clearfix = document.getElementsByClass("container clearfix").first();
                    Element zx_no_answer = container_clearfix.getElementsByClass("zx-no-answer").first();
                    if (zx_no_answer != null) {
                        //无查询结果
                        result.setResultType(ServiceResultEnum.EMPTY_RESULT);
                        return result;
                    } else {
                        Elements data = container_clearfix.getElementsByClass("cjxx-info").select("tr");
                        KaoYan kaoYan = new KaoYan();
                        kaoYan.setName(data.get(0).select("td").get(1).text());
                        kaoYan.setSignUpNumber(data.get(1).select("td").get(1).text());
                        kaoYan.setExamNumber(data.get(2).select("td").get(1).text());
                        kaoYan.setTotalScore(data.get(4).select("td").get(1).text());
                        kaoYan.setFirstScore(data.get(5).select("td").get(1).text().replace(" ", ""));
                        kaoYan.setSecondScore(data.get(6).select("td").get(1).text().replace(" ", ""));
                        kaoYan.setThirdScore(data.get(6).select("td").get(1).text().replace(" ", ""));
                        kaoYan.setFourthScore(data.get(7).select("td").get(1).text().replace(" ", ""));
                        result.setResultData(kaoYan);
                        result.setResultType(ServiceResultEnum.SUCCESS);
                        return result;
                    }
                }
                throw new ServerErrorException("查询系统异常");
            }
            throw new ServerErrorException("查询系统异常");
        } catch (IOException e) {
            log.error("查询考研成绩异常：", e);
            result.setResultType(ServiceResultEnum.TIME_OUT);
        } catch (ServerErrorException e) {
            log.error("查询考研成绩异常：", e);
            result.setResultType(ServiceResultEnum.SERVER_ERROR);
        } catch (ErrorQueryConditionException e) {
            log.error("查询考研成绩异常：", e);
            result.setResultType(ServiceResultEnum.ERROR_CONDITION);
        } catch (Exception e) {
            log.error("查询考研成绩异常：", e);
            result.setResultType(ServiceResultEnum.SERVER_ERROR);
        }
        return result;
    }
}

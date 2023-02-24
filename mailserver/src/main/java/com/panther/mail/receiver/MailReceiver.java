package com.panther.mail.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.regex.Pattern;

/**
 * @author Gin 琴酒
 * @data 2023/02/20 22:53
 **/
@RestController
@RequestMapping("/test")
public class MailReceiver {

    private final static Logger logger = LoggerFactory.getLogger(MailReceiver.class);
    @Resource
    JavaMailSender MailSender ; // 发送邮箱的bean

    @Resource
    MailProperties mailProperties;

    final String author = "Gin 琴酒";
    String title="[程序猿琴酒]获取验证码";

    @Value("${spring.mail.username}")
    String usserEmail;
//
//    static final String email1 = "2392189963@qq.com";

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam("to") String to)  {

        if(!Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", to)){
            return "不好好输邮箱你想干嘛！0-0";
        }

        try {
            String Mailbody = setMail(usserEmail);
            //logger.info(EmailRandom());
            MimeMessage mimeMessage = MailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            logger.info("from: "+usserEmail);
            messageHelper.setFrom(mailProperties.getUsername()); // 设置发件邮箱
            logger.info("To: "+to);
            messageHelper.setTo(to);   //设置收件邮箱
            messageHelper.setSubject(title); // 设置标题
            messageHelper.setText(Mailbody,true); // true表示邮箱内容为html
//            FileSystemResource file = new FileSystemResource(
//            File file = new File("图片路径");
//            helper.addAttachment("图片.jpg", file);//添加带附件的邮件
//            helper.addInline("picture",file);//添加带静态资源的邮件
            MailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return "发送成功检查邮箱信息";
    }

    private static String setMail(String email1) {
        // 生成邮箱随机验证码
        String code = EmailRandom();

        StringBuffer body = new StringBuffer();
        body.append("客官您来啦,里面请!\n\n").append("    您的验证码为: <style color='yellow'>").append(code).append("</style>\\n\\n");
        body.append("    客官请注意:需要您在收到邮件后5分钟内使用，否则该验证码将会失效。\n\n");
        return body.toString();
    }

    private static String EmailRandom(){
        int[] nums = {1,2,3,4,5,6,7,8,9,0};
        char[] chars = {'a','b','c','d','e','f','g','h','i'};
        String res = "";
        for(int i =0; i < 6; i++){
            if(i == 4){
                long round = Math.round(Math.random() * 9);
                res += chars[(int) round];
                continue;
            }
            res += nums[(int)Math.round(Math.random() * 9)];
        }
        return res;
    }
}

package com.mdg.PSYThinktank.utils;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class EmailUtils {
	
	public void sendTempPwEmail(JavaMailSender mailSender, String email) {
		final MimeMessagePreparator preparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
				helper.setFrom("officialpsythinktank@gmail.com");
				helper.setTo(email);
				helper.setSubject("임시 비밀번호를 보내드립니다.");
				helper.setText("임시비밀번호는 1111입니다. test", true);		
			}
		};
		mailSender.send(preparator);
	}	
}

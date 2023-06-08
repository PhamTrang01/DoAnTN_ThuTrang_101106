package Mail;

public class EmailConfig {
	//Nhớ tạo mật khẩu app (App Password) cho Gmail mới gửi được nhen
    //Nếu dùng mail của Hosting thì bình thường
    //Enable Override Report and Send mail in config file => src/test/resources/config/config.properties
    //OVERRIDE_REPORTS=yes
    //send_email_to_users=yes

//    public static final String SERVER = "smtp.gmail.com";
//    public static final String PORT = "587";
//
//    public static final String FROM = "tp5537029@gmail.com";
//    public static final String PASSWORD = "wvolrqmbwfklcxkq";
//
//    public static final String[] TO = {"tp5537029@gmail.com"};
//    public static final String SUBJECT = "Mail";
    public static final String SERVER = "smtp.gmail.com";
    public static final String PORT = "587";

    public static final String FROM = "tp5537029@gmail.com";
    public static final String PASSWORD = "wvolrqmbwfklcxkq";

    public static final String[] TO = {"iutrang0705220101@gmail.com"};
    public static final String SUBJECT = "DAILY REPORT - AUTOMATION FRAMEWORK";
    public static final String message = "I have some attachments for you.";
}
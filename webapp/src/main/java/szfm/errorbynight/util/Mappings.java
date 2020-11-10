package szfm.errorbynight.util;


public class Mappings {



    private Mappings(){}

    public static final String LOGIN = "login";
    public static final String REGISTRATION = "registration";
    public static final String LOGOUT = "logout";
    public static final String REDIRECT_LOGIN = "redirect:/"+LOGIN;
    public static final String REDIRECT_REGISTRATION = "redirect:/"+REGISTRATION;
    public static final String ACTIVATION = "activation";
    public static final String PROFILE = "profile";
    public static final String MESSAGES = "messages";
    public static final String SETTINGS = "settings";
    public static final String HOME = "home";
    public static final String ACCOUNT = "account";
    public static final String USERDATA = "userdata";
    public static final String CHANGE_USERNAME = "change_username";
    public static final String CHANGE_EMAIL = "change_email";
    public static final String CHANGE_PASSWORD  = "change_password";
    public static final String PRIVATE_MESSAGE  = "p_message";
    public static final String LIST_MESSAGES  = "list_messages";
    public static final String IMAGES  = "profileImg";
    public static final String THEMES  = "themes";
    public static final String THEME  = "theme";
    public static final String ERROR  = "/error";
    public static final String NEW_POST_THEME = "new_post/theme";
    public static final String NEW  = "new";
    public static final String DELETE = "delete";
    public static final String REDIRECT_THEMES = "redirect:/"+THEMES;
    public static final String THEME_NEW = THEME+"/"+NEW;
    public static final String REDIRECT_THEMES_NEW = "redirect:/"+THEME_NEW;
    public static final String ACCOUNT_PROFILE = ACCOUNT+"/"+PROFILE;
    public static final String ACCOUNT_SETTINGS = ACCOUNT+"/"+SETTINGS;
    public static final String ACCOUNT_MESSAGES = ACCOUNT+"/"+MESSAGES;
    public static final String SETTINGS_USERDATA = SETTINGS+"/"+USERDATA;
    public static final String SETTINGS_CHANGE_USERNAME = SETTINGS+"/"+CHANGE_USERNAME;
    public static final String SETTINGS_CHANGE_EMAIL = SETTINGS+"/"+CHANGE_EMAIL;
    public static final String SETTINGS_CHANGE_PASSWORD = SETTINGS+"/"+CHANGE_PASSWORD;
    public static final String FAV  = "fav";
    public static final String ADD  = "add";
    public static final String THEME_DELETE_FAV  = THEME+"/"+DELETE+"_"+FAV;
    public static final String THEME_ADD_FAV  = THEME+"/"+ADD+"_"+FAV;
}

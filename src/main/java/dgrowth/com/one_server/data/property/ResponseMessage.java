package dgrowth.com.one_server.data.property;

public class ResponseMessage {
    public static final String SUCCESS = "성공";
    public static final String FAILED_TO_SAVE_MINIO= "이미지를 업로드하는데 실패하였습니다.(Minio Object Storage)";
    public static final String FAILED_TO_SAVE_IMAGE= "이미지를 업로드하는데 실패하였습니다.";
    public static final String FAILED_TO_GET_MINIO= "이미지를 불러올 수 없습니다.(Minio Object Storage)";
    public static final String FAILED_TO_GET_IMAGE= "이미지를 불러올 수 없습니다.";
    public static final String FAILED_TO_LOGIN_KAKAO= "카카오 로그인 인증에 실패하였습니다.";
    public static final String FAILED_TO_AUTH_KAKAO= "카카오 코드 인증이 만료되었습니다. 재발급이 필요합니다.";
    public static final String FAILED_TO_SIGN_UP= "회원가입에 실패하였습니다.";
    public static final String DUPLICATED_USER = "이미 가입된 유저입니다.";
}

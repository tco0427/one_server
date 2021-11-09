package dgrowth.com.one_server.web.property;

public class ResponseMessage {

    public static final String SUCCESS = "성공";
    public static final String FAILED_TO_SAVE_MINIO = "이미지를 업로드하는데 실패하였습니다.(Minio Object Storage)";
    public static final String FAILED_TO_SAVE_IMAGE = "이미지를 업로드하는데 실패하였습니다.";
    public static final String FAILED_TO_GET_MINIO = "이미지를 불러올 수 없습니다.(Minio Object Storage)";
    public static final String FAILED_TO_GET_IMAGE = "이미지를 불러올 수 없습니다.";
    public static final String FAILED_TO_LOGIN_KAKAO = "카카오 로그인 인증에 실패하였습니다.";
    public static final String FAILED_TO_AUTH_KAKAO = "카카오 코드 인증이 만료되었습니다. 재발급이 필요합니다.";
    public static final String FAILED_TO_SIGN_UP = "회원가입에 실패하였습니다.";
    public static final String DUPLICATED_USER = "이미 가입된 유저입니다.";
    public static final String INVALID_TOKEN = "유효하지 않은 토큰이거나 전달 방식이 올바르지 않습니다.";
    public static final String EXPIRED_TOKEN = "유효기간이 만료된 토큰입니다.";
    public static final String FAILED_TO_RE_ISSUE_TOKEN = "토큰 재발급에 실패했습니다.";
    public static final String FAILED_TO_DELETE_GROUP = "그룹 삭제에 실패했습니다.";
    public static final String FAILED_TO_GET_TOKEN = "토큰 정보를 가져올 수 없습니다.";
    public static final String INVALID_USER = "토큰에 해당하는 회원 정보가 없습니다.";
    public static final String FAILED_TO_FIND_USER = "회원 정보 조회에 실패했습니다.";
}

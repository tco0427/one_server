package dgrowth.com.one_server.data.property;

public class Config {

    public static final String MINIO_BUCKET = "/bucket";
    public static final String MINIO_URL = "http://121.124.124.8:9000" + MINIO_BUCKET;
    public static final String DEFAULT_PROFILE_IMAGE_URL = MINIO_URL + "/default-profile-img.png";
}

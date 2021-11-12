package dgrowth.com.one_server.notice.dto;

import dgrowth.com.one_server.notice.domain.entity.Notice;
import dgrowth.com.one_server.notice.dto.response.NoticeResponse;
import java.util.ArrayList;
import java.util.List;

// BaseMapper 를 사용하지 않았음에 주의
public class NoticeMapper {

    public static List<NoticeResponse> multipleToResponses(List<Notice> notices, int num) {

        List<NoticeResponse> noticeResponses = new ArrayList<>();

        int length = Math.min(num, notices.size());

        for (int i = 0; i < length; i++) {

            Notice notice = notices.get(i);

            NoticeResponse noticeResponse = new NoticeResponse(notice.getId(), notice.getTitle(),
                notice.getContent());

            noticeResponses.add(noticeResponse);
        }

        return noticeResponses;
    }
}

package dgrowth.com.one_server.data.dto.mapper;

import dgrowth.com.one_server.data.dto.response.CategoryResponse;
import dgrowth.com.one_server.domain.enumeration.Category;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CategoryMapper {

    public static List<CategoryResponse> toDto() {

        List<CategoryResponse> categoryResponses = new ArrayList<>();

        for (String e : categoryMap.keySet()) {
            ColorMap colorMap = categoryMap.get(e);
            CategoryResponse response = CategoryResponse.builder().category(e).color(colorMap.color)
                .opacity(colorMap.opacity).build();

            categoryResponses.add(response);
        }

        return categoryResponses;
    }

    private static Map<String,ColorMap> categoryMap = new HashMap<>(){
        {
            put(Category.EXERCISE.getValue(), new ColorMap("#85f2b1", 0.33f));
            put(Category.STUDY.getValue(), new ColorMap("#f97e25", 0.24f));
            put(Category.CULTURE.getValue(), new ColorMap("#1f35ff", 0.24f));
            put(Category.MEAL.getValue(), new ColorMap("#c985f2", 0.33f));
        }
    };

    @AllArgsConstructor
    private static class ColorMap {
        private String color;
        private Float opacity;
    }
}

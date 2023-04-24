package nus.iss.Neko.Server.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import nus.iss.Neko.Server.models.Cat;
import nus.iss.Neko.Server.models.CatListResponse;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

@Service
public class CachingService {

    @Cacheable(value = "query", key = "#query.toString() + #pageNum.toString()")
    public CatListResponse getListOfCatsId(String url, String query, int pageNum) throws Exception {
        CatListResponse catResponse = new CatListResponse();
        JsonObject data = null;

        try {
            data = CatService.getDataFromAPI(url);
        } catch (Exception ex) {
            throw new Exception("API error");
        }

        if (data.getInt("to") == 0) {
            throw new Exception("No cats found");
        }

        String NEXT_URL = data.getJsonObject("_links")
                .getJsonObject("next")
                .getString("href");
        String _contValue = NEXT_URL.split("&")[2].substring(6);
        catResponse.setNextURL(_contValue);

        List<Cat> cats = new LinkedList<>();

        JsonArray hits = data.getJsonArray("hits");
        for (JsonValue jsonObj : hits) {
            JsonObject cat = jsonObj.asJsonObject().getJsonObject("cat");

            cats.add(CatService.getSimilarData(cat));
        }

        catResponse.setCats(cats);
        return catResponse;
    }

    @Cacheable(value = "catDetails", key = "#cat_id.substring(0, 8)")
    public Cat getCatDetails(String url, String cat_id) throws Exception {
        JsonObject data = null;

        try {
            data = CatService.getDataFromAPI(url);
        } catch (Exception ex) {
            throw new Exception("No cat!");
        }

        try {
            JsonObject cat = data.getJsonObject("cat");
            Cat catModel = CatService.getSimilarData(cat);
            catModel.setLink(cat.getString("url"));

            // int yield = cat.getInt("yield");
            // catModel.setServings(yield);

            // JsonArray ingredientLines = cat.getJsonArray("ingredientLines");
            // for (JsonValue ingredient : ingredientLines) {
            //     String ingred = ingredient.toString().substring(1, ingredient.toString().length() - 1);
            //     catModel.addIngredientLines(ingred);
            // }

            // Float totalCal = Float.valueOf(cat.get("calories").toString());
            // catModel.setCalories((int) Math.ceil(totalCal / yield));

            return catModel;
        } catch (Exception ex) {
            throw new Exception("API error");
        }
    }
}

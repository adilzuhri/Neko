package nus.iss.Neko.Server.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import nus.iss.Neko.Server.models.Cat;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class CatService implements Serializable {

    @Value("live_gMocx8E1lZtM4dLBiGxXQFF7SgjEDc7dVk9DCEnFNgtrkhUygyLPwQVfBBzEhdgn")
    private static String app_key;

    private final String DEFAULT_URL = "https://api.thecatapi.com/v1/images/search";

    public Optional<?> getCatId(String query, int pageNum, String contValue) {
        
        String url = null;
        if (contValue == null) {
            url = buildUrl(DEFAULT_URL)
                .queryParam("q", query)
                .toUriString();
        } else {
                url = buildUrl(DEFAULT_URL)
                .queryParam("q", query)
                .toUriString();
                url += "&_cont=" + contValue;
                System.out.println(">>> url: " + url);
        }
        return null;
    }

    public Optional<?> getCatDetails(String id) {
        buildUrl(DEFAULT_URL + "/" + id)
            .queryParam("id", id)
            .toUriString();
        return null;
    }

    public Optional<String> getCatLabelById(String id) {
        String url = buildUrl(DEFAULT_URL + "/" + id)
                .queryParam("id", id)
                .toUriString();

        JsonObject data = null;

        try {
            data = getDataFromAPI(url);
        } catch (Exception ex) {
            return Optional.of("API error!");
        }

        String cat_label = "";

        try {
            JsonObject cat = data.getJsonObject("cat");
            cat_label = cat.getString("label");
            return Optional.of(cat_label);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public UriComponentsBuilder buildUrl(String url) {
        UriComponentsBuilder urlB = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("app_key", app_key)
                .queryParam("type", "public");

        return urlB;
    }

    public static Cat getSimilarData(JsonObject cat) {
        Cat catModel = new Cat();

        // get cat_id
        String uri = cat.getString("uri");
        int idIndex = uri.indexOf("#");
        String cat_id = uri.substring(idIndex + 8);
        catModel.setCat_id(cat_id);

        // set UUID for easier reference
        catModel.setStoredUUID(UUID.randomUUID().toString().substring(0, 8));

        // get label
        catModel.setLabel(cat.getString("label"));

        // get image regular
        String imageLink = cat.getJsonObject("images")
                .getJsonObject("REGULAR")
                .getString("url");
        catModel.setImage(imageLink);

        return catModel;
    }

    public static JsonObject getDataFromAPI(String url) throws IOException {
        InputStream is = null;
        try {
            is = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }

        JsonReader reader = Json.createReader(is);
        JsonObject data = reader.readObject();

        return data;
    }

    public static void saveCat(Cat cat) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", app_key);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(cat.getImage()));
        body.add("sub_id", cat.getCat_id());
    }

    public void updateCat(Cat cat) {
        String url = DEFAULT_URL + "/v1/images/{id}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", app_key);
        HttpEntity<Cat> requestEntity = new HttpEntity<>(cat, headers);
        RestTemplate.exchange(url, HttpMethod.PUT, requestEntity, Void.class, cat.getCat_id());
    }

    public void deleteCat(String id) {
        String url = DEFAULT_URL + "/v1/images/{id}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", app_key);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        RestTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, id);
    }

    public static Cat getCatLabelById(Long id) {
        return null;
    }

    public static void deleteCat(Long id) {
    }
}

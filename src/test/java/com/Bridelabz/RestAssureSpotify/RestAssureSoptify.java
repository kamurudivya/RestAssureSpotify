package com.Bridelabz.RestAssureSpotify;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
/*
 * Rest Assure Spotify Application
 */

public class RestAssureSoptify {
    public String token;
    public static String userId, playlistId;

    @BeforeTest
    public void getToken(){
        token = ("Bearer BQAR3650kfW1xLFjvuXQgfWJtooaZgwoCwxfZFYpchtO_FX3j67Cf3-puLbwNeEssYdKRZR4cKTxthokMuliEhyw46SAfoct3ZkSpEdUe-gQpe3zX-n3pcePCpIquIqwHLrEp--5zcUT3DChFpOUcPx0RgtbzpOPGVnwhYQGCwrrPPpZAjXk794zo5RsN3z95-Ku2OBrmAsRoavFA4RHyQSwzuiR39wJWNXt-1Z-F93l5ivFnd_gI0I56eFIl_LK221L3x8NngZ3QZjPnpk2MXRBKWJ3IPloF0uB3w0k_GudD_ra24VCZfgQWEwRw-NgJ2uPY7r3jKA1KA\"");
    }
    //User API
    @Test
    public void getCurrentUserProfile(){
        System.out.println("----------Get Current UserProfile-------------");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/me");
        response.prettyPrint();
        userId = response.path("id");
        System.out.println("UserID: " + userId);

        response.then().assertThat().statusCode(200);
        Assert.assertEquals("31ohrg7acfda3f4ohuq6ppa2nmi4", response.jsonPath().getString("id"));
    }

    @Test
    public void getUserProfile(){
        System.out.println("---------------Get UserProfile with userId--------------");
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .header("ContentType", "application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/users/"+userId+"/");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200, response.getStatusCode());
    }

    //Playlist API
    @Test(priority = 2)
    public void newCreatePlaylist(){
        System.out.println("--------------------Create new playlist--------------------");
        String requestBody = "{\"name\":\"Sid Sreeram Playlist\",\n\"description\":\"Telugu playlist description\",\n\"public\":\"false\"}";
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .header("ContentType", "application/json")
                .header("Authorization",token)
                .body(requestBody)
                .when()
                .post("https://api.spotify.com/v1/users/31ohrg7acfda3f4ohuq6ppa2nmi4/playlists");
        response.prettyPrint();
        playlistId = response.path("id");
        System.out.println("Playlist ID: " + playlistId);

        response.then().assertThat().statusCode(201);
        Assert.assertEquals(201, response.getStatusCode());
    }


    @Test(priority = 3)
    public void playlistGetWithPlaylistId(){
        System.out.println("--------------------Get Playlist--------------------");
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .header("ContentType", "application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/playlists/41C84ji0sKngxbM0b9pK9d/");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200, response.getStatusCode());
    }


    @Test
    public void getUserPlaylist(){
        System.out.println("--------------------Get User Playlist--------------------");
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .header("ContentType", "application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/users/"+userId+"/playlists");
        response.prettyPrint();
        String firstPlaylistId = response.path("items[0].id");
        String ownerName = response.path("items[0].owner.display_name");
        System.out.println("First playlist ID: " + firstPlaylistId);
        System.out.println("Owner name: " + ownerName);

        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200, response.getStatusCode());
    }

    //Search API
    @Test
    public void searchItem(){
        System.out.println("--------------------Search item--------------------");
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .header("ContentType", "application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/search?q=track&type=track");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    // Shows API
    @Test
    public void GetShow(){
        System.out.println("---------------------- GetShow --------------------------");
        Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("id","5CfCWKI5pZ28U0uOzXkDHe")
                .when()
                .get("https://api.spotify.com/v1/shows/{id}");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void GetShowEpisodes(){
        System.out.println("-------------------- Get Show Episodes ----------------------------");
        Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("id","5CfCWKI5pZ28U0uOzXkDHe")
                .when()
                .get("https://api.spotify.com/v1/shows/{id}/episodes");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void GetSeveralShows(){
        System.out.println("------------------- Get Several Shows -----------------------------");
        Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("ids","5CfCWKI5pZ28U0uOzXkDHe,5CfCWKI5pZ28U0uOzXkDHe")
                .when()
                .get("https://api.spotify.com/v1/shows?ids={ids}");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }

    // Personalization API
    @Test
    public void GetUsersTopItems(){
        System.out.println("-------------------- Get Users Top Items--------------------");
        Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("type","artists")
                .when()
                .get("https://api.spotify.com/v1/me/top/{type}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }

    //Market API
    @Test
    public void GetAvailableMarkets(){
        System.out.println("--------------------Get Available Markets--------------------");
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .header("ContentType", "application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/markets");
        response.prettyPrint();
    }

    //Episode
    @Test
    public void GetEpisode() {
        System.out.println("-------------------- Get Episode ----------------------------");
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .pathParam("id","512ojhOuo1ktJprKbVcKyQ")
                .when()
                .get("https://api.spotify.com/v1/episodes/{id}");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200, response.getStatusCode());
    }
    @Test
    public void GetSeveralEpisode() {
        System.out.println("-------------------- Get Several Episode ----------------------------");
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .pathParam("ids","512ojhOuo1ktJprKbVcKyQ,512ojhOuo1ktJprKbVcKyQ")
                .when()
                .get("https://api.spotify.com/v1/episodes?ids={ids}");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200, response.getStatusCode());
    }

    //Browse API
    @Test
    public void GetAvailableGenreSeeds(){
        System.out.println("--------------------Get Available Genre Seeds--------------------");
        Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/recommendations/available-genre-seeds");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void GetSeveralBrowseCategories(){
        System.out.println("--------------------Get Several Browse Categories--------------------");
        Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/browse/categories");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void GetFeaturedPlaylists(){
        System.out.println("-------------------- Get Featured Playlists --------------------");
        Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/browse/featured-playlists");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void GetNewReleases(){
        System.out.println("--------------------Get New Releases--------------------");
        Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/browse/new-releases");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    //Artist API
    @Test(priority = 7)
    public void GetFollowedArtists(){
        System.out.println("-------------------- Get Followed Artists--------------------");
        Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("type","artist")
                .when()
                .get("https://api.spotify.com/v1/me/following?type={type}");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void CheckifUsersFollowPlaylist(){
        System.out.println("--------------------Check if Users Follow Playlist--------------------");
        Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("type","artist")
                .when()
                .get("https://api.spotify.com/v1/me/following?type={type}");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }

}

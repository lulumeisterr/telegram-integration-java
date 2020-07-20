package br.com.fiap.newsapi;

import java.io.IOException;
import java.util.Properties;

import com.google.gson.Gson;
import com.pengrad.telegrambot.TelegramBot;

import br.com.fiap.dto.ObjectApi;
import br.com.fiap.properties.PropertiesLoader;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsApi {
	
	static Properties prop = PropertiesLoader.getProp();
	static OkHttpClient client = new OkHttpClient();
	static Response resp;
	Gson gson;
	
	public static ObjectApi callApi(TelegramBot bot, String text) {

		String baseUrl = prop.getProperty("prop.newsApi");
		ObjectApi news = null;

		HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
		urlBuilder.addQueryParameter("q", text);
		urlBuilder.addQueryParameter("apiKey", prop.getProperty("prop.keyNewsApi"));
		urlBuilder.addQueryParameter("pageSize", "1");
		urlBuilder.addQueryParameter("sortBy", "relevancy");
		urlBuilder.addQueryParameter("language", "pt");

		String url = urlBuilder.build().toString();

		Request request = new Request.Builder().url(url).build();
		try {
			resp = client.newCall(request).execute();
			news = new Gson().fromJson(resp.body().charStream(), ObjectApi.class);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return news;
	}
	

}

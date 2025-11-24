package data_access;

import use_case.get_top_songs.TopItem;
import use_case.get_top_songs.GetTopSongsOutputData;
import use_case.get_top_songs.GetTopSongsUserDataAccessInterface;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


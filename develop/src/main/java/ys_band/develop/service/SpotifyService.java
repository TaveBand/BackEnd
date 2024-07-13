package ys_band.develop.service;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import ys_band.develop.domain.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpotifyService {

    private final SpotifyApi spotifyApi;

    public SpotifyService(@Value("${spotify.client-id}") String clientId,
                          @Value("${spotify.client-secret}") String clientSecret) {
        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(SpotifyHttpManager.makeUri("http://localhost:8888/callback"))
                .build();

        try {
            ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
            String accessToken = clientCredentialsRequest.execute().getAccessToken();
            spotifyApi.setAccessToken(accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, String>> getTracksDetails(List<Song> songs) {
        List<Map<String, String>> trackDetails = new ArrayList<>();
        try {
            for (Song song : songs) {
                String query = song.getTitle() + " " + song.getArtist();
                SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(query)
                        .limit(1)
                        .market(CountryCode.valueOf(CountryCode.KR.getAlpha2()))  // 한국 마켓으로 설정
                        .build();

                Paging<Track> trackPaging = searchTracksRequest.execute();
                Track[] tracks = trackPaging.getItems();

                for (Track track : tracks) {
                    ArtistSimplified[] artists = track.getArtists();
                    AlbumSimplified album = track.getAlbum();
                    Image[] images = album.getImages();
                    String imageUrl = images.length > 0 ? images[0].getUrl() : null;

                    if (artists.length > 0) {
                        Map<String, String> trackDetail = new HashMap<>();
                        trackDetail.put("title", track.getName());
                        trackDetail.put("artist", artists[0].getName());
                        trackDetail.put("album", album.getName());
                        trackDetail.put("imageUrl", imageUrl);
                        trackDetails.add(trackDetail);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trackDetails;
    }
}

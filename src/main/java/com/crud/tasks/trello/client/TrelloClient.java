package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Getter
@Component
@RequiredArgsConstructor
public class TrelloClient {

    private final RestTemplate restTemplate;

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @Value("${trello.app.userid}")
    private String userId;
    @Value("${trello.app.key}")
    private String trelloAppKey;
    @Value("${trello.app.token}")
    private String trelloToken;

    private URI getURI() {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + userId + "/boards/")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .queryParam("lists","all")
                .build()
                .encode()
                .toUri();
        return url;
    }

    public List<TrelloBoardDto> getTrelloBoards() {
        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(getURI(), TrelloBoardDto[].class);
        return Optional.ofNullable(boardsResponse)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/cards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId())
                .build()
                .encode()
                .toUri();
        return restTemplate.postForObject(url, null, CreatedTrelloCard.class);
    }
}
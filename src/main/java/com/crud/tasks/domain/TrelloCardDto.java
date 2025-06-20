package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloCardDto {

    @JsonProperty ("name")
    private String name;

    @JsonProperty ("desc")
    private String description;

    @JsonProperty ("pos")
    private String pos;

    @JsonProperty("listId")
    private String listId;

    @JsonProperty("badges")
    private TrelloBadgesDto badges;

}

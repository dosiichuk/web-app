package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    void testMapToBoards() {
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        TrelloListDto trelloListDto1 = new TrelloListDto("1", "Test name", false);
        TrelloListDto trelloListDto2 = new TrelloListDto("2", "Test name 1", true);
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("Test name", "1", Collections.singletonList(trelloListDto1));
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("Test name 2", "2", Collections.singletonList(trelloListDto2));
        trelloBoardDtos.add(trelloBoardDto1);
        trelloBoardDtos.add(trelloBoardDto2);

        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);

        assert trelloBoards.size() == trelloBoardDtos.size();
        assertEquals(trelloBoardDtos.get(0).getId(), trelloBoards.get(0).getId());
    }

    @Test
    void testMapToBoardsDto() {
        TrelloList list = new TrelloList("2", "List", true);
        TrelloBoard board = new TrelloBoard("20", "Board", Collections.singletonList(list));
        List<TrelloBoardDto> boardDtos = trelloMapper.mapToBoardsDto(Collections.singletonList(board));

        assertEquals(1, boardDtos.size());
        TrelloBoardDto boardDto = boardDtos.get(0);
        assertEquals("20", boardDto.getId());
        assertEquals("Board", boardDto.getName());
        assertEquals(1, boardDto.getLists().size());
        TrelloListDto listDto = boardDto.getLists().get(0);
        assertEquals("2", listDto.getId());
        assertEquals("List", listDto.getName());
        assertTrue(listDto.isClosed());
    }

    @Test
    void testMapToList() {
        TrelloListDto listDto1 = new TrelloListDto("1", "List1", false);
        TrelloListDto listDto2 = new TrelloListDto("2", "List2", true);
        List<TrelloList> lists = trelloMapper.mapToList(Arrays.asList(listDto1, listDto2));

        assertEquals(2, lists.size());
        assertEquals("1", lists.get(0).getId());
        assertEquals("List1", lists.get(0).getName());
        assertFalse(lists.get(0).isClosed());
        assertEquals("2", lists.get(1).getId());
        assertEquals("List2", lists.get(1).getName());
        assertTrue(lists.get(1).isClosed());
    }

    @Test
    void testMapToListDto() {
        TrelloList list1 = new TrelloList("3", "List3", false);
        TrelloList list2 = new TrelloList("4", "List4", true);
        List<TrelloListDto> listDtos = trelloMapper.mapToListDto(Arrays.asList(list1, list2));

        assertEquals(2, listDtos.size());
        assertEquals("3", listDtos.get(0).getId());
        assertEquals("List3", listDtos.get(0).getName());
        assertFalse(listDtos.get(0).isClosed());
        assertEquals("4", listDtos.get(1).getId());
        assertEquals("List4", listDtos.get(1).getName());
        assertTrue(listDtos.get(1).isClosed());
    }

    @Test
    void testMapToCardDto() {
        TrelloCard card = new TrelloCard("CardName", "Desc", "top", "123");
        TrelloCardDto cardDto = trelloMapper.mapToCardDto(card);

        assertEquals("CardName", cardDto.getName());
        assertEquals("Desc", cardDto.getDescription());
        assertEquals("top", cardDto.getPos());
        assertEquals("123", cardDto.getListId());
    }

    @Test
    void testMapToCard() {
        TrelloCardDto cardDto = new TrelloCardDto("CardDtoName", "DtoDesc", "bottom", "321");
        TrelloCard card = trelloMapper.mapToCard(cardDto);

        assertEquals("CardDtoName", card.getName());
        assertEquals("DtoDesc", card.getDescription());
        assertEquals("bottom", card.getPos());
        assertEquals("321", card.getListId());
    }
}

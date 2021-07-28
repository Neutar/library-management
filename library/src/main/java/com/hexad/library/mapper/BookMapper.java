package com.hexad.library.mapper;

import com.hexad.library.controller.response.BookResponse;
import com.hexad.library.domain.Book;
import com.hexad.library.service.BookDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookMapper {
    BookMapper BOOK_MAPPER = Mappers.getMapper(BookMapper.class);

    BookDto mapBookDtoFromBook(Book book);

    List<BookDto> mapBookDtoListFrom(List<Book> bookList);

    List<BookResponse> mapBookResponseListFrom(List<BookDto> bookDtoList);
}

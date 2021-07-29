package com.hexad.librarymanagement.mapper;

import com.hexad.librarymanagement.controller.response.BookResponse;
import com.hexad.librarymanagement.domain.Book;
import com.hexad.librarymanagement.service.BookDto;
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

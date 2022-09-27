package net.junhabaek.tddpractice.book.adapter.in.web;

import net.junhabaek.tddpractice.book.application.port.in.BookInfo;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface BookResponseDtoMapper {
    @Mappings({
            @Mapping(target="bookId", source = "bookInfo.id"),
            @Mapping(target="createdDate", source = "bookInfo.createdDate")
    })
    BookResponseDto.RegisterBookResponse bookInfoToRegisterBookResponse(BookInfo bookInfo);
}

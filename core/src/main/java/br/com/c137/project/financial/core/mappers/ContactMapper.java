package br.com.c137.project.financial.core.mappers;

import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.ContactGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.ContactPostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.ContactPutDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.models.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactGetDTO contactToContactGetDTO(Contact contact);
    Contact postToContact(ContactPostDTO contactPostDTO);
    Contact putToContact(ContactPutDTO contactPutDTO, @MappingTarget Contact contact);
}

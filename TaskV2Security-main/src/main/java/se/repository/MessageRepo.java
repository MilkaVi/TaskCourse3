package se.repository;

import org.springframework.data.repository.CrudRepository;
import se.domain.Message;

import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Long> {

}
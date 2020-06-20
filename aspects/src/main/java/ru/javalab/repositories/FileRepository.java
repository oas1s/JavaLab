package ru.javalab.repositories;

import ru.javalab.models.FileModel;

import java.util.Optional;

public interface FileRepository extends CrudRepository<Long, FileModel> {
    Optional<FileModel> findFileByName(String name);
}

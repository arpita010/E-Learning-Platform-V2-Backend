package com.app.filestore;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStoreRepo extends CrudRepository<FileStore, Long> {}

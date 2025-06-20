package com.univr.diabetes_logger.service;

import java.util.Optional;

/**
 * CrudService
 */
public interface CrudService<Entity> {
  Entity create(Entity entity);

  Iterable<Entity> getAll();

  Optional<Entity> getById(Integer id);

  Entity update(Integer id, Entity entity);

  Entity delete(Integer id);

}

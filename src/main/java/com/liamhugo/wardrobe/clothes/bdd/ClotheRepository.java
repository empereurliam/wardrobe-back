package com.liamhugo.wardrobe.clothes.bdd;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.CrudRepository;

public interface ClotheRepository extends CrudRepository<Clothe, Long> { }
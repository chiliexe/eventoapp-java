package com.chiliexe.eventoapp.repository;

import com.chiliexe.eventoapp.models.Evento;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface EventoRepository extends CrudRepository<Evento, Integer>{
    List<Evento> findAllByOrderByIdDesc();
}

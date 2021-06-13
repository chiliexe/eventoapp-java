package com.chiliexe.eventoapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.chiliexe.eventoapp.models.Convidado;
import com.chiliexe.eventoapp.models.Evento;

public interface ConvidadoRepository extends CrudRepository<Convidado, Integer> {
	
	List<Convidado> findByEvento(Evento evento);
}

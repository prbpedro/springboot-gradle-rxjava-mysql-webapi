package com.github.prbpedro.springboot.webapi.gradle.services;

import com.github.prbpedro.springboot.webapi.gradle.entities.DumbEntity;
import com.github.prbpedro.springboot.webapi.gradle.exceptions.ConflictException;
import com.github.prbpedro.springboot.webapi.gradle.repositories.DumbEntityRepository;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DumbEntityService {

	private final DumbEntityRepository repository;

	public DumbEntityService(DumbEntityRepository repository) {
		this.repository = repository;
	}

	public Single<List<DumbEntity>> findAll() {
		return Observable.defer(
			() -> Observable.fromIterable(repository.findAll())
		)
		.flatMap(
			entity -> {
				entity.setTransientString(entity.getId() + " Transient");
				return Observable.just(entity);
			}
		)
		.toList()
		.subscribeOn(Schedulers.io());
	}

	public Single<DumbEntity> create(Long id) {
		return Single.defer(
			() -> Single.just(id)
		)
		.flatMap(
			(idToSearch) -> Single.just(Pair.of(idToSearch, repository.existsById(idToSearch)))
		)
		.flatMap(
			(pair) -> {
				if(pair.getSecond()){
					return Single.error(new ConflictException(pair.getFirst()));
				}else{
					return Single.just(pair.getFirst());
				}
			}
		)
		.flatMap(
			(idToInsert) -> Single.just(repository.save(new DumbEntity(idToInsert)))
		)
		.subscribeOn(Schedulers.io());
	}
}

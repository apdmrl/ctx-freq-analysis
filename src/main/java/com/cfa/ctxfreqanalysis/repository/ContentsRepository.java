package com.cfa.ctxfreqanalysis.repository;

import com.cfa.ctxfreqanalysis.model.Contents;
import com.cfa.ctxfreqanalysis.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContentsRepository extends JpaRepository<Contents,Long> {

    List<Contents> findByLanguage(Language language);

    Optional<Contents> findByName(String name);
}

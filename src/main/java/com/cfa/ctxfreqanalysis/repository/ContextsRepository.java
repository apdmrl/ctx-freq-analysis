package com.cfa.ctxfreqanalysis.repository;

import com.cfa.ctxfreqanalysis.model.Contexts;
import com.cfa.ctxfreqanalysis.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContextsRepository extends JpaRepository<Contexts,Long> {

    List<Contexts> findByLanguage(Language language);

    Optional<Contexts> findByName(String name);

    Optional<Contexts> findByNameAndLanguage(String name, Language language);

    List<Contexts> findByLanguageAndParentContexts(Language language,Contexts contexts);

}

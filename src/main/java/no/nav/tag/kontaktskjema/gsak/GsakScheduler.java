package no.nav.tag.kontaktskjema.gsak;


import java.time.Instant;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockingTaskExecutor;
import no.nav.tag.kontaktskjema.Kontaktskjema;
import no.nav.tag.kontaktskjema.KontaktskjemaRepository;

@Slf4j
@Component
public class GsakScheduler {
    
    @Autowired
    private KontaktskjemaRepository kontaktskjemaRepository;    
    
    @Autowired
    private GsakOppgaveForSkjema oppgaveForSkjema;    
    
    @Autowired
    LockingTaskExecutor taskExecutor;
    
    @Scheduled(cron = "* * * * * ?")
    public void scheduledOpprettOppgaveForSkjemaer() {

        Instant lockAtMostUntil = Instant.now().plusSeconds(60);
        Instant lockAtLeastUntil = Instant.now().plusSeconds(30);

        taskExecutor.executeWithLock(
                (Runnable)this::opprettOppgaveForSkjemaer,
                new LockConfiguration("opprettOppgaveForSkjemaer", lockAtMostUntil, lockAtLeastUntil)
        );

    }
    
    private void opprettOppgaveForSkjemaer() {
        Collection<Kontaktskjema> skjemaer = kontaktskjemaRepository.findAllWithNoGsakOppgave();
        log.info("Fant {} skjemaer som ikke har gsak-oppgave", skjemaer.size());
        skjemaer.forEach(oppgaveForSkjema::opprettOppgaveOgLagreStatus);
    }
}

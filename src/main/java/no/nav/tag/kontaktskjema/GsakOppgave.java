package no.nav.tag.kontaktskjema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class GsakOppgave {
    private String id;
    private String tildeltEnhetsnr;
    private String beskrivelse;
    private String temagruppe;
    private String tema;
    private String oppgavetype;
    private String prioritet;
    private String aktivDato;
    private String fristFerdigstillelse;
}
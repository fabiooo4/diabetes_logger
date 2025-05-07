Autenticazione necessaria per l'utente

# Attori

- Medico
  - Pazienti: 1:N
  - Email
  - Info generali

- Paziente
  - Medico: 1:1
  - Terapia: 1:0-1
  - Rilevazione: 1:N
  - Info extra: 1:0-1
  - Info generali

## Oggetti
- Terapia
  - Farmaco
  - Assunzioni giornaliere
  - Quantità assunta

- Rilevazione
  - Giorno
  - Ora
  - Livello glicemico
  - Farmaco
  - Quantità assunta
  - Sintomi

# Use case

## Paziente

- Autenticarsi
- Memorizzare rilevazioni giornalere di glicemia (prima e dopo ogni pasto)
  - Sintomi
  - Assuzione della terapia
  - Eventuali altre patologie o terapie

## Medico

- Autenticarsi
- Specificare terapie che i pazienti devono seguire
  - Farmaco
  - N° assuzioni giornaliere
  - Quantità da assumere
  - Eventuali indicazioni
- Vedere i dati dei pazienti
- Aggiungere o modificare la terapia di un paziente
- Aggiungere o modificare info extra sul paziente
  - Fattori di rischio
  - Patologie pregresse
  - Comorbidità
- Vedere o modificare dati del paziente

## Sistema
- Verificare che i pazienti seguano le terapie dei medici
- Inviare notifiche ai pazienti
  - Per ricordare di fare le rilevazioni giornaliere
- Inviare notifiche al medico
  - Se il paziente non segue la terapia per più di 3 giorni
  - Se il paziente registra glicemie oltre le soglie e le modalità indicate
- Tenere traccia delle modifiche fatte dai medici

## Admin
- Inserire i dati di autenticazione per pazienti e medici

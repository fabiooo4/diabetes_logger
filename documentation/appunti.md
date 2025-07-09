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
  - Rilevazione glicemia
  - Eventuali altre patologie o terapie

## Medico

- Autenticarsi
- Specificare terapie che i pazienti devono seguire
  - Farmaco
  - N° assuzioni giornaliere
  - Quantità da assumere
  - Eventuali indicazioni
- Vedere i dati dei pazienti
- Modificare dati dei pazienti
- Aggiungere o modificare la terapia di un paziente
- Aggiungere o modificare info extra sul paziente
  - Fattori di rischio
  - Patologie pregresse
  - Comorbidità
- Vedere l'ultimo medico che ha modificato un dato sui pazienti ????

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

# Endpoints

## Admin
- CRUD/**

## Patients
#### UserController
- GET/users/{loggedId} può accedere solo alle risorse dell'utente autenticato, non degli altri.

#### ReportsController
- GET+POST+PUT+DELETE/reports/{loggedId}

#### NotificationController
- GET+DELETE/notifications/{loggedId}

## Medics
#### UserController
- GET/users/{loggedId} può accedere solo alle risorse dell'utente autenticato, non degli altri.

#### ReportsController
- GET/reports/{anyUserId}
- GET/reports

#### NotificationController
- GET+DELETE/notifications/{loggedId}

#### TherapiesController
- GET+POST+PUT/therapies
- GET/therapies/{id}

#### PatientsController
- GET/patients
- GET+PATCH/patients/{id} può modificare solo le note extra

## Log dei medici

- id del change
- medic che ha effettuato il change
- a quale paziente ha effettuato il change
- cosa ha modificato (Added therapy, Removed therapy, ecc.)
// come farebbero dei commit su git

## Figure 13: Modificare e aggiungere gli endpoints alle funzioni
## Figure 15: Modificare sequence diagram da Model -> Controller

/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2025-06-20 23:46:57.

export interface Patient {
    firstName: string;
    lastName: string;
    birthDate: Date;
    referralMedic: Medic;
}

export interface Medic {
    firstName: string;
    lastName: string;
    patients: Patient[];
}

export interface Therapy {
}

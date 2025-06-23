/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2025-06-23 21:27:52.

export interface Patient {
    id: number;
    firstName: string;
    lastName: string;
    birthDate: Date;
    referralMedic: Medic;
}

export interface Medic {
    id: number;
    firstName: string;
    lastName: string;
}

export interface Therapy {
}

/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2025-06-21 00:03:13.

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

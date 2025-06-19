/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2025-06-19 22:02:04.

export interface Patient {
    id: number;
    firstName: string;
    lastName: string;
    age: number;
    referralMedic: Medic;
}

export interface Medic {
    id: number;
    name: string;
}

export interface Therapy {
}

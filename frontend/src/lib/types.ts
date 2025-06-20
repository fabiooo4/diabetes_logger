/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2025-06-20 13:45:06.

export interface Patient {
    id: number;
    firstName: string;
    lastName: string;
    age: number;
    email: string;
    referralMedic: Medic;
}

export interface Medic {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
}

export interface Therapy {
}

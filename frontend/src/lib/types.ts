/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2025-06-25 02:16:24.

export interface User {
    id: number;
    email: string;
    password: string;
    role: Role;
    patient: Patient;
    medic: Medic;
}

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

export type Role = "PATIENT" | "MEDIC" | "ADMIN";

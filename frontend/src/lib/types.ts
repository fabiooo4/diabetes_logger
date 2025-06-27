/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2025-06-27 23:03:19.

export interface User {
    id: number;
    email: string;
    role: Role;
    medic: Medic;
    password: string;
}

export interface Patient {
    id: number;
    firstName: string;
    lastName: string;
    birthDate: Date;
    referralMedic: Medic;
    user: User;
    therapy: Therapy;
}

export interface Medic {
    id: number;
    firstName: string;
    lastName: string;
}

export interface Therapy {
    id: number;
    medicine: string;
    dailyIntake: number;
    amount: number;
    directions: string;
}

export type Role = "PATIENT" | "MEDIC" | "ADMIN";

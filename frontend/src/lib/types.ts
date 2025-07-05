/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2025-07-05 14:30:37.

export interface User {
    notifications: Notification[];
    id: number;
    email: string;
    role: Role;
    patient: Patient;
    medic: Medic;
    password: string;
}

export interface Patient {
    id: number;
    firstName: string;
    lastName: string;
    birthDate: string;
    riskFactor: string;
    previousPatologies: string;
    medicNotes: string;
    referralMedic: Medic;
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

export interface Report {
    id: number;
    glycemiaLevel: number;
    dateTime: string;
    beforeMeal: boolean;
    symptoms: string;
    notes: string;
    medicine: string;
    amount: number;
    patient: Patient;
}

export interface Notification {
    id: number;
    message: string;
    seen: boolean;
    createdAt: string;
    user: User;
}

export type Role = "PATIENT" | "MEDIC" | "ADMIN";

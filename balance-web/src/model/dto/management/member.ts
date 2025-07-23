import type { PageSearch } from "..";

export type MemberSearch = {
  planId?: number;
  expiredFrom?: string; // e.g., '2025-07-13'
  expiredTo?: string;
  keyword?: string;
} & PageSearch

export type MemberListItem = {
  id: number;
  name: string;
  email: string;
  phone: string;
  address: string;
  enabledAt: string; // ISO 8601 date string, e.g., '2025-07-13'
  expiredAt: string;
  planId: number;
  planName: string;
};


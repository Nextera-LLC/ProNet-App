export class Experience {
  experienceId : number;
  userId : number;
  companyName: string;
  title: string;
  startDate: string;  // yyyy-MM-dd
  endDate?: string | null;
  location?: string | null;
  isCurrent: boolean;
  description?: string | null;
}

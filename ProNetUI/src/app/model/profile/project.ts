export class Project {
    projectId: number | null;
    userId: number;
  
    title: string;
    description: string;
    url: string;
  
    // stored as ISO date string (yyyy-MM-dd) in Angular
    startDate: string | null;
    endDate: string | null;
  
    createdAt?: string;
    updatedAt?: string;
}

export class Education {
    educationId: number | null;
    userId: number;
  
    institution: string;
    degree: string;
    fieldOfStudy: string;
  
    startDate: string | null;   // yyyy-MM-dd
    endDate: string | null;
  
    grade: string;
    description: string;
  
    createdAt?: string;         // returned from backend
    updatedAt?: string;
  }
  
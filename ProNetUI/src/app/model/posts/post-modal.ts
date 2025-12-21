export class PostModal {
    postId?: number;
    caption: string = '';
  
    // You can store full user object or just userId depending on your API.
    userId?: number;
  
    // Optional: if API returns user object for feed
    user?: {
      userId: number;
      firstName?: string;
      lastName?: string;
      headLine?: string;
      profileImageUrl?: string;
    };
  
    createdDate?: string; // ISO string (recommended)
    updatedDate?: string; // ISO string (recommended)
  
    visibility: Visibility = Visibility.PUBLIC;
  
    // Optional relations (usually not fully loaded in feed responses)
    comments?: any[];
    reactions?: any[];
    shares?: any[];
  
    constructor(init?: Partial<PostModal>) {
      Object.assign(this, init);
    }
  }

  export enum Visibility {
    PRIVATE = 'PRIVATE',
    PUBLIC = 'PUBLIC',
    FRIENDS_ONLY = 'FRIENDS_ONLY',
  }
  
  
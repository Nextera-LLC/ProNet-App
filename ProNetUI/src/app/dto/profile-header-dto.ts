export class ProfileHeaderDto {
    // Basic info
  firstName : string;
  lastName : string;
  headLine : string;

  // Location
  location :{
  country : string;
  state : string;
  city : string;
  }

  //contact
  contact:{
    phone : string;
    email : string;
    linkedIn : string;
  }
  
}

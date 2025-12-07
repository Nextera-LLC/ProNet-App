export class UserSkill {
    userSkillId?: number;           // matches backend ID
    name: string;
    level: 'Beginner' | 'Intermediate' | 'Advanced' | '';
    yearsExperience?: number | null;
    userId?: number;
}
